
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