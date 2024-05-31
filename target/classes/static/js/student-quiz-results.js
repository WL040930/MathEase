document.addEventListener("DOMContentLoaded", async function () {
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

                const questionResponse = await fetch(`/api/questionsFetch/${topicId}`);
                if (!questionResponse.ok) {
                    throw new Error('Failed to fetch questions');
                }
                const questions = await questionResponse.json();

                topicInfoPanel.innerHTML = generateTopicInfoHTML(topicDetails, questions);


                // another session
                const tableRows = document.querySelectorAll('#topicInfoPanel table tbody tr');
                tableRows.forEach(row => {
                    row.addEventListener('click', async () => {
                        tableRows.forEach(r => r.classList.remove('selected'));
                        row.classList.add('selected');
                        const questionId = questions[row.rowIndex - 1].questionId;

                        try {
                            const questionDetailsResponse = await fetch(`/api/questionStudent/${questionId}`);
                            if (!questionDetailsResponse.ok) {
                                throw new Error('Failed to fetch question details');
                            }
                            const questionDetails = await questionDetailsResponse.json();

                            // logic here
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
                    </div>
                </div>
            `;
        }

        const questionsHTML = `
            <table>
                <thead>
                    <tr>
                        <th>Number</th>
                        <th>Question Text</th>
                        <th>User Answer</th>
                        <th>Result</th>
                    </tr>
                </thead>
                <tbody>
                    ${questions.map((question, index) => `
                        <tr>
                            <td>${index + 1}</td>
                            <td>${question.quiz}</td>
                            <td>${question.userAnswer}</td>
                            <td>${displayResultIcon(question.userAnswer, question.correctOptions)}</td>
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
                </div>
            </div>
        `;
    }

    function displayResultIcon(userAnswer, correctOptions) {
        const correctAnswers = correctOptions.split(',').map(option => option.trim());
        const isCorrect = correctAnswers.includes(userAnswer);

        if (isCorrect) {
            return '<span style="color: green; font-size: 1.2em;">✔️</span>';
        } else {
            return '<span style="color: red; font-size: 1.2em;">❌</span>';
        }
    }
});


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

document.addEventListener("DOMContentLoaded", function () {
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
            <p><strong>Question:</strong> <br>${questionDetails.quiz}</p>
            <p><strong>Correct Answer:</strong> <span style="color: #32CD32">${questionDetails.correctOptions}</span></p>
            <p><strong>Wrong Answers:</strong><span style="color: red">${questionDetails.wrongOptions}</span></p>
            <p><strong>User Answer:</strong>${questionDetails.userAnswer}</p>
            ${questionDetails.filePath ? `<img style="width: 90%; margin: 0 auto; max-width: 100%; overflow-x: auto" src="/data/${questionDetails.filePath}" alt="question image">` : ''}
        </div>
    `;
}
