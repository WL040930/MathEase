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
                    <button class="control-button edit-button">Edit</button>
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
                    <button class="control-button edit-button">Edit</button>
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
        modal.style.display = 'none';
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

    // Perform form validation
    if (!questionText || !correctAnswer || !wrongAnswer1 || !wrongAnswer2 || !wrongAnswer3) {
        validationText.innerHTML = 'Please fill in all fields.';
        validationText.style.color = 'red';
        return;
    } else {
        validationText.innerHTML = '';
    }

    // Prepare the data to be sent to the backend
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

    // Send the data to the backend using fetch and a POST request
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
            console.log('Item added successfully:', data);
            closeAddModal();
        })
        .catch(error => {
            console.error('Error adding item:', error);
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
        modal.style.display = 'none';
    }
}

document.addEventListener("DOMContentLoaded", function() {
    const viewButton = document.querySelector('.view-button');
    if (viewButton) {
        viewButton.addEventListener('click', openViewModel);
    }

    const closeButton = document.querySelector('.close');
    if (closeButton) {
        closeButton.addEventListener('click', closeViewModel);
    }
});

function displayQuestionDetails(questionDetails) {
    const modalContent = document.querySelector('.addContainer');

    // Populate modal with question details
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
