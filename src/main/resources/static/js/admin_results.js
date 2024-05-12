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