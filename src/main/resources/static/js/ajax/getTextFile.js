function getFileText(filePath) {

    /*Request in order to get the content of the file*/
    $.get(
        "/getTextFile",
        {
            filePath: filePath
        },
        function (answer) {
            displayModalWindow(answer);
        }
    );


}