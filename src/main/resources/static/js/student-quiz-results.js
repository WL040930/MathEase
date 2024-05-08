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

                const questionResponse = await fetch(`/api/questionsFetch/${topicId}`);
                if (!questionResponse.ok) {
                    throw new Error('Failed to fetch questions');
                }
                const questions = await questionResponse.json();

                topicInfoPanel.innerHTML = generateTopicInfoHTML(topicDetails, questions);

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
                            <td>${checkAnswer(question.userAnswer, question.correctOptions)}</td>
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

    function checkAnswer(userAnswer, correctOptions) {
        const correctAnswers = correctOptions.split(',').map(option => option.trim());
        return correctAnswers.includes(userAnswer) ? 'Correct' : 'Incorrect';
    }
});
