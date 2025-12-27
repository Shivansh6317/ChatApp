let stompClient;
let page = 0;
const size = 20;

const username = localStorage.getItem("username");
const roomId = localStorage.getItem("roomId");

document.getElementById("roomName").innerText = `Room: ${roomId}`;
document.getElementById("userName").innerText = `User: ${username}`;

function connect() {
    const socket = new SockJS("/ws");
    stompClient = Stomp.over(socket);

    stompClient.connect({}, () => {
        stompClient.subscribe(`/topic/room/${roomId}`, msg => {
            showMessage(JSON.parse(msg.body), true);
        });
    });
}

// Load old messages (pagination)
function loadMessages() {
    fetch(`/api/chat/${roomId}/messages?page=${page}&size=${size}`)
        .then(res => res.json())
        .then(data => {
            data.content.reverse().forEach(m => showMessage(m, false));
            page++;
        });
}

function sendMessage() {
    const input = document.getElementById("messageInput");
    const text = input.value.trim();
    if (!text) return;

    stompClient.send(`/app/chat/${roomId}`, {}, JSON.stringify({
        sender: username,
        content: text
    }));

    input.value = "";
}

function showMessage(msg, scroll) {
    const box = document.getElementById("chatBox");
    const div = document.createElement("div");

    div.className = "message " + (msg.sender === username ? "me" : "other");
    div.innerHTML = `<strong>${msg.sender}</strong><br>${msg.content}`;

    box.appendChild(div);
    if (scroll) box.scrollTop = box.scrollHeight;
}

function leaveRoom() {
    stompClient.disconnect();
    localStorage.clear();
    window.location.href = "index.html";
}

loadMessages();
connect();
