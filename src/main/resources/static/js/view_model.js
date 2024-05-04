document.addEventListener("DOMContentLoaded", function () {
    const viewButtons = document.querySelectorAll('#topicInfoPanel table tbody tr .view-button');
    const addContainer = document.querySelector('.addContainer');

    viewButtons.forEach(viewButton => {
        viewButton.addEventListener('click', async () => {
            const row = viewButton.closest('tr');
            if (!row) return;

            const questionId = row.dataset.questionId;
            const rowIndex = parseInt(row.dataset.rowIndex);

            try {
                const response = await fetch(`/api/question/${questionId}`);
                if (!response.ok) {
                    throw new Error('Failed to fetch question details');
                }

                const questionDetails = await response.json();

                addContainer.innerHTML = `
                    <h2>Question Details</h2>
                    <p>Row Number: ${rowIndex + 1}</p>
                    <p>Question ID: ${questionDetails.questionId}</p>
                    <p>Question Text: ${questionDetails.questionText}</p>
                    <p>Correct Answer: ${questionDetails.correctAnswer}</p>
                    <p>Wrong Answers:</p>
                    <ul>
                        <li>${questionDetails.wrongAnswer1}</li>
                        <li>${questionDetails.wrongAnswer2}</li>
                        <li>${questionDetails.wrongAnswer3}</li>
                    </ul>
                `;
            } catch (error) {
                console.error('Error fetching question details:', error);
                // Display error message or handle error accordingly
                addContainer.innerHTML = '<p>Error fetching question details.</p>';
            }
        });
    });
});
