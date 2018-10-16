function doCopyFileRequest(target, source, treeNode) {


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