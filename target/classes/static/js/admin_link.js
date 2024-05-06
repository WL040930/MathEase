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

                const linkResponse = await fetch(`/api/links/${topicId}`);
                if (!linkResponse.ok) {
                    throw new Error('Failed to fetch Links');
                }
                const Links = await linkResponse.json();

                topicInfoPanel.innerHTML = generateTopicInfoHTML(topicDetails, Links);

                const tableRows = document.querySelectorAll('#topicInfoPanel table tbody tr');
                tableRows.forEach(row => {
                    row.addEventListener('click', async () => {
                        tableRows.forEach(r => r.classList.remove('selected'));
                        row.classList.add('selected');
                        const linkId = Links[row.rowIndex - 1].linkId;

                        try {
                            const linkDetailsResponse = await fetch(`/api/links/${questionId}`);
                            if (!linkDetailsResponse.ok) {
                                throw new Error('Failed to fetch link details');
                            }
                            const linkDetails = await linkDetailsResponse.json();

                        } catch (error) {
                            console.error('Error fetching link details:', error);
                        }
                    });
                });

            } catch (error) {
                console.error('Error fetching data:', error);
            }
        });
    });









function generateTopicInfoHTML(topicDetails, links) {
    const haslinks = links && links.length > 0;

    if (!haslinks) {
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
                    <p>No links for this chapter.</p>
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

    const linksHTML = `
        <table>
            <thead>
                <tr>
                    <th>No.</th>
                    <th>URL name</th>
                </tr>
            </thead>
            <tbody>
                ${links.map((link, index) => `
                    <tr>
                        <td>${index + 1}</td>
                        <td>${link.linkTitle}</td>
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
                ${linksHTML}
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

function clearAddModal() {
    document.getElementById('linkTitle').value = '';
    document.getElementById('linkUrl').value = '';
}

function addLink() {
    const linkTitle = document.getElementById('linkTitle').value;
    const linkURL = document.getElementById('linkUrl').value;
    const topicId = document.querySelector('.clickable-topic.active').dataset.topicId;

    const formData = new FormData();
    formData.append('linkTitle', linkTitle);
    formData.append('linkURL', linkURL);
    formData.append('topicId', topicId);

    fetch(`/api/addLinks`, {
        method: 'POST',
        body: formData
    })
    .then(response => {
        if (!response.ok) {
            throw new Error('Failed to add link');
        }
        closeAddModal();
        clearAddModal();
        reloadLinkTable(topicId).then(r => console.log('Link table reloaded'));
    })
    .catch(error => {
        closeAddModal();
        clearAddModal();
        reloadLinkTable(topicId).then(r => console.log('Link table reloaded'));
    });
}


async function reloadLinkTable(topicId) {
    try {
        const linkResponse = await fetch(`/api/links/${topicId}`);
        if (!linkResponse.ok) {
            throw new Error('Failed to fetch updated links');
        }
        const links = await linkResponse.json();

        const tableBody = document.querySelector('#topicInfoPanel table tbody');
        tableBody.innerHTML = '';

        links.forEach((link, index) => {
            const newRow = document.createElement('tr');
            newRow.innerHTML = `
                <td>${index + 1}</td>
                <td>${link.linkTitle}</td>
            `;
            tableBody.appendChild(newRow);

            newRow.addEventListener('click', async () => {
                const tableRows = document.querySelectorAll('#topicInfoPanel table tbody tr');
                tableRows.forEach(r => r.classList.remove('selected'));

                newRow.classList.add('selected');

                const linkId = links[index].linkId;

                try {
                    const linkDetailsResponse = await fetch(`/api/link/${linkId}`);
                    if (!linkDetailsResponse.ok) {
                        throw new Error('Failed to fetch link details');
                    }
                    const linkDetails = await linkDetailsResponse.json();

                    // Handle displaying link details (e.g., function to display details)

                } catch (error) {
                    console.error('Error fetching link details:', error);
                }
            });
        });
    } catch (error) {
        console.error('Error reloading link table:', error);
    }
}
