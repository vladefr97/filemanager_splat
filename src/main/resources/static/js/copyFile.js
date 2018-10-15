function copyFile() {
    var sourcePath =  $(this).attr('id');
    var targetPath =$(this.parentElement.parentElement.parentElement.childNodes[0]).attr('id');
    doCopyFileRequest(targetPath,sourcePath);

}