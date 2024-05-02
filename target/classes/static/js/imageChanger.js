function handleFileInput() {
    var fileInput = document.getElementById('file');
    var previewImage = document.getElementById('previewImage');
    var selectedFile = fileInput.files[0];

    if (selectedFile) {
        var reader = new FileReader();

        reader.onload = function (e) {
            previewImage.src = e.target.result;
            previewImage.style.display = 'block';
        };

        reader.readAsDataURL(selectedFile);
    } else {
        // Clear the preview if no file is selected
        previewImage.src = '';
        previewImage.style.display = 'none';
    }
}