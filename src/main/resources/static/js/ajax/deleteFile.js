function deleteFile(filePath) {


    /*delete file request to the server*/
    $.get(
        "/deleteFile",
        {
            filePath:filePath
        },
        function (message) {
            showPrompt(message.messageText, message.success);

        }
    );

}