document.addEventListener('DOMContentLoaded', () => {
    const speakButton = document.getElementById('speakButton');
    const stopButton = document.getElementById('stopButton');
    const contentHeader = document.querySelector('#content');
    let utterance = null;

    const extractVisibleText = (element) => {
        let textContent = '';
        element.childNodes.forEach(node => {
            if (node.nodeType === Node.TEXT_NODE) {
                textContent += node.textContent.trim();
            }
        });
        return textContent;
    };

    const speakText = (text) => {
        const synth = window.speechSynthesis;
        utterance = new SpeechSynthesisUtterance(text);

        utterance.lang = 'en-US';
        utterance.pitch = 1;
        utterance.rate = 1;

        synth.speak(utterance);
    };

    const stopSpeaking = () => {
        if (utterance) {
            window.speechSynthesis.cancel();
        }
    };

    speakButton.addEventListener('click', () => {
        const textToSpeak = extractVisibleText(contentHeader);
        speakText(textToSpeak);
    });

    stopButton.addEventListener('click', () => {
        stopSpeaking();
    });
});
