var mesId = 0;
var myName = "User 1";
var mesList = [];
function run() {
    var appContainer = document.getElementsByClassName('main')[0];

    appContainer.addEventListener('click', delegateEvent);
    appContainer.addEventListener('keydown', delegateEvent);

    mesList = loadMessage()|| [newMessage('Chat:')];
    mesId = mesList[mesList.length - 1].id;

    myName = mesList[mesList.length - 1].username || 'User 1';
    var input = document.getElementById('name');
    input.value = myName;

    render(mesList);
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
    mesId++;
    mesList.push(newMessage(text));
    render(mesList);
    userMsg.value = "";

    var box = document.getElementById('chatBox');
    box.scrollTop += 9999;
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
            render(mesList);
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

    text.innerHTML = messag.message;
    textName.innerHTML = messag.username;
    time.textContent = messag.time;

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

    if(messag.username!==myName){
        deleteBut.hidden = true;
        editBut.hidden = true;
    }
    if(messag.edited ===true){
        var edit = document.createElement('div');
        edit.className.add('edit');
        edit.setAttribute('id','edit');
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
    messag.deleted = true;
    messag.message = 'Message deleted.';
    render(mesList);
}
function changeMessage(messag) {
    var text = document.getElementById('myMessage' + messag.id);
    var input = document.getElementById('editText' + messag.id);

    input.value = messag.message;

    input.hidden = false;
    text.hidden = true;

    input.addEventListener('keydown', function (e) {
        if (e.keyCode === 13) {
            messag.message = input.value;
            input.hidden = true;
            text.hidden = false;
            messag.edited = true;
            render(mesList);
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
        username: myName,
        message: text,
        id: mesId,
        time: new Date().toTimeString().replace(/.*(\d{2}:\d{2}:\d{2}).*/, "$1"),
        deleted: false,
        edited: false
    };
}
function loadMessage() {
    if (typeof(Storage) === "undefined") {
        alert('localStorage is not accessible');
        return;
    }

    var item = localStorage.getItem("messages");
    return item && JSON.parse(item);
}

function saveMessage(listToSave) {
    if (typeof(Storage) === "undefined") {
        alert('localStorage is not accessible');
        return;
    }
    localStorage.setItem("messages", JSON.stringify(listToSave));
}