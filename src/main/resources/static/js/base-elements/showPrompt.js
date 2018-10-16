function showPrompt(text, isSucceed) {
    var prompt = $(".prompt")[0];
    prompt.innerText = text;
    if (isSucceed) {
        prompt.setAttribute("class", "prompt success");
    }
    else {
        prompt.setAttribute("class", "prompt fault");
    }

    $(prompt).fadeIn();
    setTimeout(function () {
        $(prompt).fadeOut(2000);
    }, 1000);
}

function showInfoPrompt(text) {
    var prompt = $(".prompt")[0];

    prompt.innerText = text;
    prompt.setAttribute("class", "prompt info");
    $(prompt).fadeIn();
    setTimeout(function () {
        $(prompt).fadeOut(2000);
    }, 1000);
}