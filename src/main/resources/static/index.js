function joinRoom() {
    const username = document.getElementById("username").value.trim();
    const roomId = document.getElementById("roomId").value.trim();

    if (!username || !roomId) {
        alert("Username and Room ID required");
        return;
    }

    localStorage.setItem("username", username);
    localStorage.setItem("roomId", roomId);

    window.location.href = "chat.html";
}
