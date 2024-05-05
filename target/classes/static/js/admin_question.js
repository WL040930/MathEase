document.addEventListener("DOMContentLoaded", async function() {
    const topicList = document.querySelectorAll('.clickable-topic');
    const topicInfoPanel = document.getElementById('topicInfoPanel');

    topicList.forEach(topic => {
    topic.addEventListener('click', async () => {

    topicList.forEach(t => t.classList.remove('active'));

    topic.classList.add('active');

    const topicId = topic.dataset.topicId;

    try {
        const topicResponse = await fetch(`/api/topics/${topicId}`);
        if (!topicResponse.ok) {
            throw new Error('Failed to fetch topic details');
        }
        const topicDetails = await topicResponse.json();

        const questionResponse = await fetch(`/api/questions/${topicId}`);
        if (!questionResponse.ok) {
            throw new Error('Failed to fetch questions');
        }
        const questions = await questionResponse.json();

        topicInfoPanel.innerHTML = generateTopicInfoHTML(topicDetails, questions);

        const tableRows = document.querySelectorAll('#topicInfoPanel table tbody tr');
        tableRows.forEach(row => {
            row.addEventListener('click', async () => {
                tableRows.forEach(r => r.classList.remove('selected'));
                row.classList.add('selected');
                const questionId = questions[row.rowIndex - 1].questionId;

                try {
                    const questionDetailsResponse = await fetch(`/api/question/${questionId}`);
                    if (!questionDetailsResponse.ok) {
                        throw new Error('Failed to fetch question details');
                    }
                    const questionDetails = await questionDetailsResponse.json();

                    displayQuestionDetails(questionDetails);
                    DisplayEditQuestionDetails(questionDetails);
                } catch (error) {
                    console.error('Error fetching question details:', error);
                }
            });
        });

    } catch (error) {
            console.error('Error fetching data:', error);
        }
    });
});





function generateTopicInfoHTML(topicDetails, questions) {
    const hasQuestions = questions && questions.length > 0;

    if (!hasQuestions) {
    return `
            <div class="topic-info">
                <div class="top-panel">
                    <div style="float: left; width: 20%">
                        <img src="${topicDetails.picturePath}" alt="topic image" class="topicTop">
                    </div>
                    <div style="float: right; width: 79%">
                        <h2>Chapter ${topicDetails.topicId}</h2>
                        <p>Topic Name: ${topicDetails.topicName}</p>
                    </div>
                    <div style="clear: both"></div>
                </div>
                <div class="table-panel">
                    <p>No questions for this chapter.</p>
                </div>
                <div class="controller">
                    <button class="control-button view-button" onclick="openViewModel()">View</button>
                    <button class="control-button add-button" onclick="openAddModal()">Add</button>
                    <button class="control-button edit-button" onclick="openEditModel()">Edit</button>
                    <button class="control-button delete-button">Delete</button>
                </div>
            </div>
            `;
}

    const questionsHTML = `
            <table>
                <thead>
                    <tr>
                        <th id="table-column1">Number</th>
                        <th id="table-column2">Question Text</th>
                    </tr>
                </thead>
                <tbody>
                    ${questions.map((question, index) => `
                        <tr>
                            <td>${index + 1}</td>
                            <td>${question.question}</td>
                        </tr>
                    `).join('')}
                </tbody>
            </table>
        `;

    return `
            <div class="topic-info">
                <div class="top-panel">
                    <div style="float: left; width: 20%">
                        <img src="${topicDetails.picturePath}" alt="topic image" class="topicTop">
                    </div>
                    <div style="float: right; width: 79%">
                        <h2>Chapter ${topicDetails.topicId}</h2>
                        <p>Topic Name: ${topicDetails.topicName}</p>
                    </div>
                    <div style="clear: both"></div>
                </div>
                <div class="table-panel">
                    ${questionsHTML}
                </div>
                <div class="controller">
                    <button class="control-button view-button" onclick="openViewModel()">View</button>
                    <button class="control-button add-button" onclick="openAddModal()">Add</button>
                    <button class="control-button edit-button" onclick="openEditModel()">Edit</button>
                    <button class="control-button delete-button">Delete</button>
                </div>
            </div>
        `;
}
});






function openAddModal() {
    const modal = document.getElementById('addModal');
    if (modal) {
        modal.style.display = 'block';
    }
}

function closeAddModal() {
    const modal = document.getElementById('addModal');
    if (modal) {
        modal.classList.add('fadeOut');
        setTimeout(() => {
            modal.style.display = 'none';
            modal.classList.remove('fadeOut');
        }, 300);
    }
}

document.addEventListener("DOMContentLoaded", function() {
    const addButton = document.querySelector('.add-button');
    if (addButton) {
        addButton.addEventListener('click', openAddModal);
    }

    const closeButton = document.querySelector('.close');
    if (closeButton) {
        closeButton.addEventListener('click', closeAddModal);
    }
});

function submitItem() {
    const validationText = document.getElementById('validation-text');
    const questionText = document.getElementById('question').value.trim();
    const correctAnswer = document.getElementById('answer1').value.trim();
    const wrongAnswer1 = document.getElementById('answer2').value.trim();
    const wrongAnswer2 = document.getElementById('answer3').value.trim();
    const wrongAnswer3 = document.getElementById('answer4').value.trim();
    const pictureFile = document.getElementById('add-image').files[0];
    var topicId = document.querySelector('.active').dataset.topicId;

    if (!questionText || !correctAnswer || !wrongAnswer1 || !wrongAnswer2 || !wrongAnswer3) {
        validationText.innerHTML = 'Please fill in all fields.';
        validationText.style.color = 'red';
        return;
    } else {
        validationText.innerHTML = '';
    }

    const formData = new FormData();
    formData.append('questionText', questionText);
    formData.append('correctAnswer', correctAnswer);
    formData.append('wrongAnswer1', wrongAnswer1);
    formData.append('wrongAnswer2', wrongAnswer2);
    formData.append('wrongAnswer3', wrongAnswer3);
    formData.append('topicId', topicId);

    if (pictureFile) {
        formData.append('picture', pictureFile);
    }

    fetch('/api/addItem', {
        method: 'POST',
        body: formData
    })
        .then(response => {
            if (!response.ok) {
                throw new Error('Failed to add item');
            }
            return response.json();
        })
        .then(data => {
            var topicId = document.querySelector('.active').dataset.topicId;
            closeAddModal();
            reloadQuestionTable(topicId).then(r => console.log('Reloaded question table'));
        })
        .catch(error => {
            var topicId = document.querySelector('.active').dataset.topicId;
            closeAddModal();
            reloadQuestionTable(topicId).then(r => console.log('Reloaded question table'));
        });
}












function openViewModel() {
    const modal = document.getElementById('viewModal');
    if (modal) {
        modal.style.display = 'block';
    }
}

function closeViewModal() {
    const modal = document.getElementById('viewModal');
    if (modal) {
        modal.classList.add('fadeOut');
        setTimeout(() => {
            modal.style.display = 'none';
            modal.classList.remove('fadeOut');
        }, 300);
    }
}

document.addEventListener("DOMContentLoaded", function() {
    const viewButton = document.querySelector('.view-button');
    if (viewButton) {
        viewButton.addEventListener('click', openViewModel);
    }

    const closeButton = document.querySelector('.close');
    if (closeButton) {
        closeButton.addEventListener('click', closeViewModal);
    }
});

function displayQuestionDetails(questionDetails) {
    const modalContent = document.querySelector('.addContainer');

    modalContent.innerHTML = `
        <div class="addContainer">
            <p><strong>Question:</strong> <br>${questionDetails.question}</p>
            <p><strong>Correct Answer:</strong> <span style="color: #32CD32">${questionDetails.correctAnswer}</span></p>
            <p><strong>Wrong Answers:</strong> 
                <span style="color: red;"><br>1. ${questionDetails.wrongAnswer1}</span>
                <span style="color: red;"><br>2. ${questionDetails.wrongAnswer2}</span>
                <span style="color: red;"><br>3. ${questionDetails.wrongAnswer3}</span>
            </p>
            ${questionDetails.picturePath ? `<img style="width: 90%; margin: 0 auto;" src="/data/${questionDetails.picturePath}" alt="question image">` : ''}
        </div>
    `;
}









function openEditModel() {
    const modal = document.getElementById('editModal');
    if (modal) {
        modal.style.display = 'block';
    }
}

function closeEditModal() {
    const modal = document.getElementById('editModal');
    if (modal) {
        modal.classList.add('fadeOut');
        setTimeout(() => {
            modal.style.display = 'none';
            modal.classList.remove('fadeOut');
        }, 300);
    }
}

document.addEventListener("DOMContentLoaded", function() {
    const viewButton = document.querySelector('.edit-button');
    if (viewButton) {
        viewButton.addEventListener('click', openEditModel);
    }

    const closeButton = document.querySelector('.close');
    if (closeButton) {
        closeButton.addEventListener('click', closeEditModal);
    }
});

function DisplayEditQuestionDetails(questionDetails) {
    const modalContent = document.querySelector('.editContainer');
    const questionId = document.querySelector('.selected').dataset.questionId;

    // Populate modal with question details
    modalContent.innerHTML = `
        <div class="editContainer">
            <p>
                <strong>Question:</strong> <br>
                <textarea id="questionTextArea" name="question" cols="30" rows="2">${questionDetails.question}</textarea>
            </p>
            <input type="hidden" name="edit-questionId" value="${questionDetails.questionId}">
            <p>
                <strong>Correct Answer:</strong> <br>
                <input type="text" id="edit-answer1" name="edit-answer1" value="${questionDetails.correctAnswer}">
            </p>
            <p><strong style="margin-bottom: 10px">Wrong Answers:</strong>
                <input type="text" id="edit-answer2" name="edit-answer2" value="${questionDetails.wrongAnswer1}">
                <input type="text" id="edit-answer3" name="edit-answer3" value="${questionDetails.wrongAnswer2}">
                <input type="text" id="edit-answer4" name="edit-answer4" value="${questionDetails.wrongAnswer3}">
            </p>
            <strong>Picture:</strong> <br>
            <input type="file" id="edit-image" name="edit-image" style="margin-top: 10px; margin-bottom: 10px">
            ${questionDetails.picturePath ? `<img style="width: 90%; margin: 0 auto;" src="/data/${questionDetails.picturePath}" alt="question image">` : ''}
            
            <br>
            <div style="margin-bottom: 20px;margin-top:20px;" id="editValidation"></div>
            <button class="edit-button" onclick="submitEditItem(${questionDetails.questionId})">Submit</button>
        </div>
    `;
}

function submitEditItem(questionId) {
    const validationText = document.getElementById('editValidation');
    const questionText = document.getElementById('questionTextArea').value.trim();
    const correctAnswer = document.getElementById('edit-answer1').value.trim();
    const wrongAnswer1 = document.getElementById('edit-answer2').value.trim();
    const wrongAnswer2 = document.getElementById('edit-answer3').value.trim();
    const wrongAnswer3 = document.getElementById('edit-answer4').value.trim();
    const pictureFile = document.getElementById('edit-image').files[0];

    // Basic client-side validation
    if (!questionText || !correctAnswer || !wrongAnswer1 || !wrongAnswer2 || !wrongAnswer3) {
        validationText.innerHTML = 'Please fill in all fields.';
        validationText.style.color = 'red';
        return;
    } else {
        validationText.innerHTML = '';
    }

    // Prepare FormData object to send to the server
    const formData = new FormData();
    formData.append('questionId', questionId);
    formData.append('questionText', questionText);
    formData.append('correctAnswer', correctAnswer);
    formData.append('wrongAnswer1', wrongAnswer1);
    formData.append('wrongAnswer2', wrongAnswer2);
    formData.append('wrongAnswer3', wrongAnswer3);

    if (pictureFile) {
        formData.append('picture', pictureFile);
    }

    // Send POST request to the server
    fetch('/api/addQuestion', {
        method: 'POST',
        body: formData
    })
        .then(response => {
            if (!response.ok) {
                throw new Error('Failed to edit item');
            }
            return response.json();
        })
        .then(data => {
            var topicId = document.querySelector('.active').dataset.topicId;
            closeEditModal();
            reloadQuestionTable(topicId).then(r => console.log('Reloaded question table'));
        })
        .catch(error => {
            var topicId = document.querySelector('.active').dataset.topicId;
            closeEditModal();
            reloadQuestionTable(topicId).then(r => console.log('Reloaded question table'));
        });
}

async function reloadQuestionTable(topicId) {
    try {
        const questionResponse = await fetch(`/api/questions/${topicId}`);
        if (!questionResponse.ok) {
            throw new Error('Failed to fetch updated questions');
        }
        const questions = await questionResponse.json();

        const tableBody = document.querySelector('#topicInfoPanel table tbody');
        tableBody.innerHTML = ''; // Clear existing table rows

        questions.forEach((question, index) => {
            const newRow = document.createElement('tr');
            newRow.innerHTML = `
                <td>${index + 1}</td>
                <td>${question.question}</td>
            `;
            tableBody.appendChild(newRow);

            // Add click event listener to the new row
            newRow.addEventListener('click', async () => {
                // Remove 'selected' class from all table rows
                const tableRows = document.querySelectorAll('#topicInfoPanel table tbody tr');
                tableRows.forEach(r => r.classList.remove('selected'));

                // Add 'selected' class to the clicked row
                newRow.classList.add('selected');

                const questionId = questions[index].questionId;

                try {
                    const questionDetailsResponse = await fetch(`/api/question/${questionId}`);
                    if (!questionDetailsResponse.ok) {
                        throw new Error('Failed to fetch question details');
                    }
                    const questionDetails = await questionDetailsResponse.json();

                    displayQuestionDetails(questionDetails);
                    DisplayEditQuestionDetails(questionDetails);
                } catch (error) {
                    console.error('Error fetching question details:', error);
                }
            });
        });
    } catch (error) {
        console.error('Error reloading question table:', error);
    }
}
