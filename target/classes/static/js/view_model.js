// This event listener triggers when the DOM content is fully loaded
document.addEventListener("DOMContentLoaded", function () {
    // Select all the view buttons within the topicInfoPanel table
    const viewButtons = document.querySelectorAll('#topicInfoPanel table tbody tr .view-button');
    // Select the container where question details will be displayed
    const addContainer = document.querySelector('.addContainer');

    // Iterate through each view button
    viewButtons.forEach(viewButton => {
        // Add a click event listener to each view button
        viewButton.addEventListener('click', async () => {
            // Find the closest row to the clicked view button
            const row = viewButton.closest('tr');
            // If no row is found, exit the function
            if (!row) return;

            // Extract question ID and row index from the row's dataset
            const questionId = row.dataset.questionId;
            const rowIndex = parseInt(row.dataset.rowIndex);

            try {
                // Fetch question details from the API endpoint
                const response = await fetch(`/api/question/${questionId}`);
                // Check if the response is successful
                if (!response.ok) {
                    throw new Error('Failed to fetch question details');
                }

                // Parse the response JSON to get question details
                const questionDetails = await response.json();

                // Display question details in the addContainer
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
                // Log and handle errors when fetching question details
                console.error('Error fetching question details:', error);
                // Display an error message in the addContainer
                addContainer.innerHTML = '<p>Error fetching question details.</p>';
            }
        });
    });
});
