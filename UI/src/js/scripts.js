var mesId = 0;
var myName = "User 1";
//var mesList = [];
var Application = {
    mesList: [],
    mainUrl: 'http://localhost:8080/chat',
    token: 'TN11EN',
    connection: null
};
function run() {
    var appContainer = document.getElementsByClassName('main')[0];

    appContainer.addEventListener('click', delegateEvent);
    appContainer.addEventListener('keydown', delegateEvent);

    loadMessage();
    mesId = Application.mesList[Application.mesList.length - 1].id;

    myName = Application.mesList[Application.mesList.length - 1].username || 'User 1';
    var input = document.getElementById('name');
    input.value = myName;

    render(Application.mesList);
}
function delegateEvent(evtObj) {
    if ((evtObj.type === 'click') && evtObj.target.classList.contains('sendMessage')) {
        onSendButtonClick(evtObj);
    }
    if (evtObj.type === 'click' && evtObj.target.classList.contains('editName')) {
        onChangeNameButtonClick(evtObj);
    }
}
function onSendButtonClick() {
    var userMsg = document.getElementById('userMsg');
    var text = userMsg.value;
    if (text != "") {
        mesId++;
        var mes = newMessage(text);
        ajax('POST', Application.mainUrl, JSON.stringify(mes), function () {

            Application.mesList.push(mes);
            render(Application.mesList);

            userMsg.value = "";
            var box = document.getElementById('chatBox');
            box.scrollTop += 9999;
        });
    }
}

function onChangeNameButtonClick() {
    if (document.getElementById('name').value !== "") {
        var input = document.getElementById('name');
        var str = document.getElementsByClassName('myName');

        if (input.value !== "") {

            var butDel = document.getElementsByClassName('deleteButton');
            var butEdit = document.getElementsByClassName('editButton');

            for (var i = 0; i < str.length; i++) {
                str[i] = input.value;
            }
            for (var i = 0; i < butDel.length; i++)
                if (input.value !== myName) {
                    butEdit[i].hidden = true;
                    butDel[i].hidden = true;
                }
            myName = input.value;
            render(Application.mesList);
        }
    }
}
function add(messag) {

    var divItem = document.createElement('ul');
    var divName = document.createElement('ul');
    var textName = document.createElement('div');
    var text = document.createElement('div');
    var time = document.createElement('div');
    var deleteBut = document.createElement('button');
    var editBut = document.createElement('button');
    var input = document.createElement('input');

    deleteBut.classList.add('deleteButton');
    editBut.classList.add('editButton');
    time.classList.add('time');
    divItem.classList.add('item');
    textName.classList.add('myName');

    time.setAttribute('id', 'timeData');
    divItem.setAttribute('id', 'divId' + messag.id);
    text.setAttribute('id', 'myMessage' + messag.id);
    input.setAttribute('id', 'editText' + messag.id);
    input.setAttribute('class', 'In');

    text.innerHTML = messag.text;
    textName.innerHTML = messag.username;
    time.textContent = messag.timestamp;

    if (!messag.deleted) {
        deleteBut.addEventListener('click', function () {
            deleteMessage(messag);
        });
        editBut.addEventListener('click', function () {
            changeMessage(messag);
        });
        divItem.appendChild(deleteBut);
        divItem.appendChild(editBut);
    }
    divItem.appendChild(text);
    divItem.appendChild(input);
    divName.appendChild(time);
    divName.appendChild(textName);

    input.hidden = true;
    text.hidden = false;

    document.getElementById('messages').appendChild(divName);
    document.getElementById('messages').appendChild(divItem);

    if (messag.username !== myName) {
        deleteBut.hidden = true;
        editBut.hidden = true;
    }
    if (messag.edited === true) {
        var edit = document.createElement('div');
        edit.className.add('edit');
        edit.setAttribute('id', 'edit');
        edit.innerHTML = 'Edited';
        divName.appendChild(edit);
    }

}
function render(list) {
    document.getElementById('messages').innerHTML = "";

    for (var i = 0; i < list.length; i++) {
        add(list[i]);
    }

    saveMessage(list);
    var box = document.getElementById('chatBox');
    box.scrollTop += 9999;
}
function deleteMessage(messag) {
    var mes = {
        id: messag.id
    };
    ajax('DELETE', Application.mainUrl, JSON.stringify(mes), function () {
        messag.deleted = true;
        messag.text = 'Message deleted.';
        render(Application.mesList);
    });
}
function changeMessage(messag) {
    var text = document.getElementById('myMessage' + messag.id);
    var input = document.getElementById('editText' + messag.id);

    input.value = messag.text;

    input.hidden = false;
    text.hidden = true;

    input.addEventListener('keydown', function (e) {
        if (e.keyCode === 13) {
            messag.text = input.value;
            input.hidden = true;
            text.hidden = false;
            var mes = {
                id: messag.id,
                text: messag.text
            };
            ajax('PUT', Application.mainUrl, JSON.stringify(mes), function () {
                loadMessage()
            });
            messag.edited = true;
            render(Application.mesList);
        }
        if (e.keyCode === 27) {
            input.hidden = true;
            text.hidden = false;
            messag.edited = false;
        }
    });
}
function newMessage(text) {
    return {
        id: mesId,
        username: myName,
        timestamp: new Date().toTimeString().replace(/.*(\d{2}:\d{2}:\d{2}).*/, "$1"),
        text: text,
        deleted: false,
        edited: false
    };
}

function ajax(method, url, data, continueWith) {
    var xhr = new XMLHttpRequest();

    //  continueWithError = continueWithError || defaultErrorHandler;
    xhr.open(method || 'GET', url, true);

    xhr.onload = function () {
        if (xhr.readyState !== 4)
            return;

        if (xhr.status != 200) {
            defaultErrorHandler('Error on the server side, response ' + xhr.status);
            return;
        }

        if (isError(xhr.responseText)) {
            defaultErrorHandler('Error on the server side, response ' + xhr.responseText);
            return;
        }
        continueWith(xhr.responseText);
        var error = document.getElementsByClassName('error')[0];
        error.innerHTML = '';
        Application.connection = true;
    };

    xhr.ontimeout = function () {
        showError();
    };

    xhr.onerror = function (e) {
        showError();
    };

    xhr.send(data);
}
function showError() {
    var err = document.getElementsByClassName('error')[0];
    err.innerHTML = '<img class="error" src="http://mediad.publicbroadcasting.net/p/wusf/files/styles/related/public/201206/error2.png" align="right" width="5%" height="5%" alt="Error">';
}
function defaultErrorHandler(message) {
    console.error(message);
    // output(text);
}

function isError(text) {
    if (text == "")
        return false;

    try {
        var obj = JSON.parse(text);
    } catch (ex) {
        return true;
    }

    return !!obj.error;
}
function loadMessage() {
    var url = Application.mainUrl + '?token=' + Application.token;

    ajax('GET', url, null, function (responseText) {
        var response = JSON.parse(responseText);
        Application.mesList = response.messages;
        render(Application.mesList);
    });
    if (Application.mesList == null) {
        Application.mesList = [];
    }
}

function saveMessage(listToSave) {
    if (typeof(Storage) === "undefined") {
        alert('localStorage is not accessible');
        return;
    }
    localStorage.setItem("messages", JSON.stringify(listToSave));
}