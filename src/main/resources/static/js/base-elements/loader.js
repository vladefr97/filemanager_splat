function setLoader(treeNode) {

    var img = treeNode.childNodes[0];
    img.style.display = "none";
    // element.removeChild(img);
    var loader = document.createElement('div');
    loader.setAttribute("class", "loader");
    $(loader).appendTo(treeNode);
}

function resetLoader(treeNode) {
    var img = treeNode.childNodes[0];
    img.style.display = "inline";
    treeNode.removeChild(treeNode.childNodes[2]);

}