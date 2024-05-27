function toggleSubTopic(iconElement) {
    var subtopicBox = iconElement.closest('.subtopic-section');
    var subSections = subtopicBox.querySelectorAll('.SubSection-box');

    var isExpanded = subtopicBox.classList.toggle('expanded');

    if (isExpanded) {
        iconElement.textContent = 'keyboard_arrow_down';
        subSections.forEach(function (subSection) {
            subSection.style.display = 'block';

            // Set subsection icons to arrow right when subtopic is expanded
            var subIcon = subSection.querySelector('.material-icons');
            subIcon.textContent = 'keyboard_arrow_right';
        });
    } else {
        iconElement.textContent = 'keyboard_arrow_right';
        subSections.forEach(function (subSection) {
            subSection.style.display = 'none';

            // Ensure all subsections are collapsed
            subSection.classList.remove('expanded');

            // Stop speech synthesis when subsection is collapsed
            var radioIcon = subSection.querySelector('.radio-icon');
            stopSpeak(radioIcon);
        });
    }
}

function toggleSubSection(iconElement) {
    var subSectionBox = iconElement.closest('.SubSection-box');
    var content = subSectionBox.querySelector('.content');
    var question = subSectionBox.querySelector('.question');

    subSectionBox.classList.toggle('expanded');
    content.classList.toggle('expanded');
    question.classList.toggle('expanded');

    // Check if the subsection is being collapsed
    if (!subSectionBox.classList.contains('expanded')) {
        // Reset subsection icons to arrow right
        iconElement.textContent = 'keyboard_arrow_right';

        // Stop speech synthesis when subsection is collapsed
        var radioIcon = subSectionBox.querySelector('.radio-icon');
        stopSpeak(radioIcon);
    }

    if (subSectionBox.classList.contains('expanded')) {
        iconElement.textContent = 'keyboard_arrow_down';
    }
}

function speak(text, radioIcon) {
    var utterance = new SpeechSynthesisUtterance(text);
    window.speechSynthesis.speak(utterance);

    radioIcon.classList.add('checked');
}

function stopSpeak(radioIcon) {
    window.speechSynthesis.cancel();

    radioIcon.classList.remove('checked');
}

function handleRadioClick(event, id) {
    event.stopPropagation();

    var radioIcon = event.currentTarget;
    if (radioIcon.classList.contains('checked')) {
        stopSpeak(radioIcon);
    } else {
        var text = "";
        if (id) {
            var explanationDiv = document.getElementById(id);
            if (explanationDiv) {
                text = explanationDiv.textContent;
            }
        }
        speak(text, radioIcon);
    }
}