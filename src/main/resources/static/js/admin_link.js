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

                            displayDeleteQuestionDetails(questionDetails)
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
                    <button class="control-button delete-button" onclick="openDeleteModel()">Delete</button>
                </div>
            </div>
        `;
    }

    const questionsHTML = `
        <table>
            <thead>
                <tr>
                    <th>No.</th>
                    <th>URL name</th>
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
                <button class="control-button delete-button" onclick="openDeleteModel()">Delete</button>
            </div>
        </div>
    `;
    }
});



