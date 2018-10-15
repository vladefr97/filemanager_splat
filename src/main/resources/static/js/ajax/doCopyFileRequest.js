function doCopyFileRequest(target, source) {

    $.get(
        "/copyFile",
        {
            targetFile: target,
            sourceFile: source
        },
        function (message) {
            showPrompt(message.messageText, message.success);

        }
    );

}