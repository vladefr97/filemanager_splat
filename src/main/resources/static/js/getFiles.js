function getDataFromAPI(element) {


    setItemSelected(element);
    var request = new XMLHttpRequest();
    var elementID = ($(element)).attr("id");
    var isDir = ($(element)).attr("data-isDirectory");

    if (isDir == "false") {
        getFileText(elementID)
        return;
    }
    var url = "getFile/" + elementID.toString().replace(new RegExp("/", "g"), "<prefix>");

    request.open("GET", url);
    request.send();


    request.onreadystatechange = function () {
        if (request.readyState == 4 && request.status == 200)//request
        {
            var answer = request.responseText;
            var files = JSON.parse(answer);

            var ulElement = document.createElement('ul');
            ulElement.setAttribute("class", "list-group");
            for (var i = 0; i < files.length; i++) {
                var liElement = document.createElement('li');
                var divElement = document.createElement('div');
                //  var innerDiv = document.createElement('div');
                var spanElement = document.createElement('span');
                var img = document.createElement('img');
                if (files[i].directory.toString() == "true") {
                    img.setAttribute("src", "https://icon-icons.com/icons2/548/PNG/32/1455554839_line-60_icon-icons.com_53339.png")

                }
                else {
                    img.setAttribute("src", "https://icon-icons.com/icons2/93/PNG/32/page_document_16748.png")

                }
                //outerDiv.setAttribute("class","out-div");
                divElement.setAttribute("data-isDirectory", files[i].directory.toString());
                divElement.setAttribute("onclick", "getDataFromAPI(this)");
                divElement.setAttribute("class", "tree-node");
                divElement.id = files[i].absolutePath;
                spanElement.innerText = files[i].fileName;
                divElement.appendChild(img);
                divElement.appendChild(spanElement);
                liElement.appendChild(divElement);


                // liElement.appendChild(pElement);
                ulElement.appendChild(liElement);
            }

            if (elementID == "content") {
                $(ulElement).appendTo(element);

            } else {

                var parentElement = element.parentElement;
                $(ulElement).appendTo(parentElement);
            }
        }


    };


}



