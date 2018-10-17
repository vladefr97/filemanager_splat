function doCopyFileRequest(target, source, treeNode) {
    /*copy file request to the server*/
    $.get(
        "/copyFile",
        {
            targetFile: target,
            sourceFile: source
        },
        function (message) {
            showPrompt(message.messageText, message.success);
            resetLoader(treeNode);

        }
    );
    showInfoPrompt("Начато копирование файла: " + source);

}