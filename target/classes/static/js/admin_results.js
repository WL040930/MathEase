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

                const linkResponse = await fetch(`/api/results/${topicId}`);
                if (!linkResponse.ok) {
                    throw new Error('Failed to fetch Links');
                }
                const Links = await linkResponse.json();

                topicInfoPanel.innerHTML = generateTopicInfoHTML(topicDetails, Links);

            } catch (error) {
                console.error('Error fetching data:', error);
            }
        });
    });
});


function generateTopicInfoHTML(topicDetails, links) {

    const linksTableHTML = `
        <table class="results-table">
            <caption>Results</caption>
            <tr>
                <td>Total Answers:</td>
                <td>${links.totalAnswers}</td>
            </tr>
            <tr>
                <td>Total Users:</td>
                <td>${links.totalUsers}</td>
            </tr>
            <tr>
                <td>Total Corrects:</td>
                <td>${links.correctAnswers}</td>
            </tr>
            <tr>
                <td>Total Wrongs:</td>
                <td>${links.incorrectAnswers}</td>
            </tr>
            <tr>
                <td>Average Percentage:</td>
                <td>${links.averageScore}%</td>
            </tr>
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
                ${linksTableHTML}
            </div>
            <div class="controller">
                <button class="control-button view-button" onclick="openViewModel(${links.correctAnswers}, ${links.incorrectAnswers})">View</button>
            </div>
        </div>
    `;
}

function openViewModel(correct, incorrect) {
    const modal = document.getElementById('viewModal');
    if (modal) {
        modal.style.display = 'block';
        displayLinkDetails(correct, incorrect);
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
        viewButton.addEventListener('click', function() {
            openViewModel();
        });
    }

    const closeButton = document.querySelector('.close');
    if (closeButton) {
        closeButton.addEventListener('click', closeViewModal);
    }
});


function displayLinkDetails(correct, incorrect) {
    const modalContent = document.querySelector('#viewModal .addContainer');

    if (modalContent) {
        const canvas = document.createElement('canvas');
        canvas.id = 'myChart';
        canvas.width = 400;
        canvas.height = 200;
        
        modalContent.innerHTML = '';
        modalContent.appendChild(canvas);

        renderChart(correct, incorrect);
    } else {
        console.error('Modal content element not found.');
    }
}

function renderChart(correct, incorrect) {
    const ctx = document.getElementById('myChart').getContext('2d');

    const chartData = {
        labels: ['Correct', 'Incorrect'],
        datasets: [{
            label: 'Answer Distribution',
            data: [correct, incorrect],
            backgroundColor: ['#36a2eb', '#ff6384']
        }]
    };

    const options = {
        responsive: true,
        maintainAspectRatio: false,
        legend: {
            position: 'top',
        },
        title: {
            display: true,
            text: 'Answer Distribution'
        }
    };

    new Chart(ctx, {
        type: 'pie',
        data: chartData,
        options: options
    });
}
