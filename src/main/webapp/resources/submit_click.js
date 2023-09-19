function submit_click(id) {
    $.ajax({
        type: "POST",
        url: root + "tobasket",
        data: id,
        contentType: 'application/json'
    });
}

