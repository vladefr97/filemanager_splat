$(document).ready(function () {

    /*Displaying the root files of the computer*/
    displayRootFiles();

});

function displayRootFiles() {

    /*Sending request to server*/
    $.get(
        "/rootFiles",
        function (files) {

            var olElement = document.createElement('ol');

            for (var i = 0; i < files.length; i++) {
                var liElement = document.createElement('li');

                var divElement = document.createElement('div');
                var spanElement = document.createElement('span');
                var img = document.createElement('img');
                if (files[i].directory.toString() == "true") {
                    img.setAttribute("src", "https://icon-icons.com/icons2/548/PNG/32/1455554839_line-60_icon-icons.com_53339.png")

                }
                else {
                    img.setAttribute("src", "https://icon-icons.com/icons2/93/PNG/32/page_document_16748.png")

                }

                /*creating views of the received files*/
                divElement.setAttribute("data-isDirectory", files[i].directory.toString());
                divElement.setAttribute("ondblclick", "displayNode(this)");
                divElement.setAttribute("onclick", "setItemSelected(this)");
                divElement.setAttribute("class", "tree-node ui-state-default");
                divElement.setAttribute("data-clicked", "false");
                divElement.id = files[i].absolutePath;
                spanElement.innerText = files[i].fileName;
                divElement.appendChild(img);
                divElement.appendChild(spanElement);
                liElement.appendChild(divElement);
                olElement.appendChild(liElement);
                olElement.setAttribute("class", "sortable");

            }
            var content = document.getElementById("content");
            $(olElement).appendTo(content);
            setSortable();
        }
    );

}

