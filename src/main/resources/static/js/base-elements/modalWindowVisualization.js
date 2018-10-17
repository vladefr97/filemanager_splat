function displayModalWindow(text) {
    var modal = document.getElementById("modal");
    var span = document.getElementsByClassName("close")[0];

    span.onclick = function () {
        modal.style.display = "none";

    };
    modal.style.display = "block";
    var modalContent = document.getElementsByClassName("modal-content")[0];
    modalContent.innerText = text;

}

function displayCreateWindow(isFile) {
    var modal = document.getElementById("create-window");
    var span = document.getElementsByClassName("close")[1];
    var createFileBtn = document.getElementById("create-file-button");
    var cancelCreateBtn = document.getElementById("cancel-create-button");
    var textInput = $("#create-window input")[0];

    textInput.onfocus = function () {
        textInput.value = "";
    };

    span.onclick = function () {
        modal.style.display = "none";

    };
    textInput.onblur = function () {
        if (textInput.value === "")
            textInput.value = "Введите имя файла";
    };
    createFileBtn.onclick = function () {
        if (textInput != null) {
            var fileName = textInput.value;
            var selectedDirectory = document.getElementsByClassName("tree-node-selected")[0];
            var isDirectory = $(selectedDirectory).attr("data-isdirectory");
            if (selectedDirectory === undefined || isDirectory === "false") {
                showPrompt("Выберите родительскую папку...", false);
                span.click();
            }
            createFile(fileName, $(selectedDirectory).attr('id'), isFile);
            span.click();
            if (selectedDirectory.hasChildNodes()) {
                displayNode(selectedDirectory);
                displayNode(selectedDirectory);
            } else {
                displayNode(selectedDirectory);
            }

        }


    };
    cancelCreateBtn.onclick = function () {

        modal.style.display = "none";
    };

    modal.style.display = "block";
}

function displayRenameWindow() {
    var modal = document.getElementById("rename-window");
    var span = document.getElementById("close-rename");
    var renameFileBtn = document.getElementById("rename-file-button");
    var cancelRenameBtn = document.getElementById("cancel-rename-button");
    var textInput = $("#rename-window input")[0];

    span.onclick = function () {
        modal.style.display = "none";

    };
    textInput.onfocus = function () {
        textInput.value = "";
    };
    textInput.onblur = function () {
        if (textInput.value === "")
            textInput.value = "Введите имя файла";
    };
    renameFileBtn.onclick = function () {
        if (textInput != null) {
            var fileName = textInput.value;
            var selectedDirectory = document.getElementsByClassName("tree-node-selected")[0];
            if (selectedDirectory === undefined) {
                showPrompt("Выберите файл...", false);
                span.click();
            }

            var currFile = document.getElementsByClassName("tree-node-selected")[0];
            var parentDir = currFile.parentElement.parentElement.parentElement.childNodes[0];
            renameFile($(parentDir).attr('id'), $(selectedDirectory).attr('id'), fileName);

            span.click();
            displayNode(parentDir);
            displayNode(parentDir);

        }
    };
    cancelRenameBtn.onclick = function () {

        modal.style.display = "none";
    };

    modal.style.display = "block";


}

function displayDeleteWindow() {
    var modal = document.getElementById("delete-window");
    var span = document.getElementById("close-delete");
    var deleteFileBtn = document.getElementById("delete-file-button");
    var cancelDeleteBtn = document.getElementById("cancel-delete-button");

    span.onclick = function () {
        modal.style.display = "none";

    };

    deleteFileBtn.onclick = function () {

        var selectedDirectory = document.getElementsByClassName("tree-node-selected")[0];
        if (selectedDirectory === undefined) {
            showPrompt("Выберите файл...", false);
            span.click();
        }

        var currFile = document.getElementsByClassName("tree-node-selected")[0];
        var parentDir = currFile.parentElement.parentElement.parentElement.childNodes[0];
        deleteFile($(selectedDirectory).attr('id'));

        span.click();
        displayNode(parentDir);
        displayNode(parentDir);


    };
    cancelDeleteBtn.onclick = function () {

        modal.style.display = "none";
    };

    modal.style.display = "block";


}