function nodeAnimaiton(element) {
    var isDirectory = $(element).attr("data-isDirectory");
    var isClicked = $(element).attr("data-clicked");
    if (isDirectory == "true") {
        if (isClicked == "true")
            collapseNode(element);
        else
            openNode(element);
    } else {
        animateFile(element)
    }
}

function collapseNode(element) {

    var parent = element.parentElement;

    parent.removeChild(parent.childNodes[1]);
    // childUl.style.display = "none";

    //document.removeChild(childUl);

    element.setAttribute("data-clicked", "false");


}

function openNode(element) {
    var img = element.childNodes[0];
    img.style.display="none";
   // element.removeChild(img);
    var loader = document.createElement('div');
    loader.setAttribute("class", "loader");
    $(loader).appendTo(element);

}

function animateFile(element) {

}

