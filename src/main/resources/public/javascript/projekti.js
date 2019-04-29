function searchUsers() {
    var input = document.getElementById("searchUsers");
    var filter = input.value.toUpperCase();
    var list = document.getElementById("userList");
    var li = list.getElementsByTagName("li");

    for (var i = 0; i < li.length; i++) {
        var a = li[i].getElementsByClassName("username")[0];
        var txtValue = a.textContent || a.innerText;
        if (txtValue.toUpperCase().indexOf(filter) > -1) {
            li[i].style.display = "";
        } else {
            li[i].style.display = "none";
        }
    }
}

function showEdit() {
    var buttons = document.getElementsByClassName("btn");
    for (var i = 0; i < buttons.length; i++) {
        buttons[i].style.display = "block";
    }
    document.getElementById("showEditButton").style.display = "none";
}
