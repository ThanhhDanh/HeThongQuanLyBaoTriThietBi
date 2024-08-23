<%-- 
    Document   : index
    Created on : Aug 19, 2024, 8:43:54 PM
    Author     : Acer
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/security/tags" %>
<%@taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles" %>



<div class="chat-container">
    <div class="sidebar">
        <h6>Online Members</h6>
        <ul id="onlineMembers">
            <!-- Danh sách thành viên online sẽ được thêm vào đây -->
        </ul>
    </div>

    <div id="chatWindow" class="chat-window">
        <ul id="messageList">
            <!-- Hiện tin nhắn -->
        </ul>
        <div class="input-container">
            <input type="text" id="message" placeholder="Tin nhắn..." />
            <button onclick="sendMessage()">Gửi</button>
        </div>
    </div>
</div>

<style>
    .chat-container {
        display: flex;
        height: 100vh;
        width: 100%;
        font-family: Arial, sans-serif;
    }

    .sidebar {
        width: 20%;
        background-color: #2c3e50;
        color: #ecf0f1;
        padding: 20px;
        box-sizing: border-box;
    }

    .sidebar h6 {
        margin-top: 0;
        border-bottom: 1px solid #ecf0f1;
        padding-bottom: 10px;
    }

    .sidebar ul {
        list-style-type: none;
        padding: 0;
        margin-top: 20px;
    }

    .sidebar ul li {
        padding: 10px;
        border-bottom: 1px solid #ecf0f1;
    }

    .chat-window {
        width: 80%;
        display: flex;
        flex-direction: column;
        justify-content: space-between;
        background-color: #ecf0f1;
        box-sizing: border-box;
        padding: 20px;
    }

    #messageList {
        list-style-type: none;
        padding: 0;
        margin: 0;
        flex-grow: 1;
        overflow-y: auto;
        max-height: calc(100vh - 120px);
    }

    #messageList li {
        margin-bottom: 10px;
        padding: 10px;
        border-radius: 5px;
        max-width: 30%;
        word-wrap: break-word;
    }

    .chat-message {
        color: #2c3e50;
        margin-left: auto;
        text-align: right;
    }

    .event-message {
        color: #2c3e50;
        margin-right: auto;
        text-align: left;
    }

    .sender {
        font-weight: bold;
        font-size: 10px;
    }

    .message-content {
        margin: 0;
        font-size: 14px;
    }

    .input-container {
        display: flex;
        align-items: center;
        padding: 10px 0;
        border-top: 1px solid #ccc;
        background-color: #ecf0f1;
    }

    .input-container input {
        flex-grow: 1;
        padding: 10px;
        border: 1px solid #ccc;
        border-radius: 5px;
        margin-right: 10px;
    }

    .input-container button {
        padding: 10px 20px;
        background-color: #2980b9;
        color: white;
        border: none;
        border-radius: 5px;
        cursor: pointer;
    }

    .input-container button:hover {
        background-color: #3498db;
    }

</style>

<script>
    function sendMessage() {
        const message = document.getElementById('message').value;
        const messageType = 'CHAT';
        const sender = '<s:authentication property="principal.username"/>';

        if (message.trim() === '') {
            return;
        }

        const db = firebase.database();
        const newMessageRef = db.ref('messages').push();
        newMessageRef.set({
            sender: sender,
            content: message,
            type: messageType
        }).then(() => {
            console.log('Tin nhắn gửi thành công');
            document.getElementById('message').value = ''; // Clear input field
        }).catch((error) => {
            console.error('Lỗi gửi tin nhắn:', error);
        });
    }

    function setupEventListeners() {
        const messageInput = document.getElementById('message');

        // Thêm sự kiện keypress cho ô nhập tin nhắn
        messageInput.addEventListener('keypress', function (e) {
            if (e.key === 'Enter') {
                e.preventDefault();
                sendMessage();
            }
        });
    }

    function fetchMessages() {
        fetch('/bamboo/pollMessages')
                .then(response => response.json())
                .then(messages => {
                    const messageList = document.getElementById('messageList');
                    messageList.innerHTML = '';  // Clear the message list

                    messages.forEach(msg => {
                        if (msg && msg.sender && msg.content && msg.type) {
                            // Create a new list item for each message
                            const li = document.createElement('li');

                            li.className = msg.type === 'CHAT' ? 'chat-message' : 'event-message';

                            li.innerHTML = `<span class="sender">${msg.sender}</span> <p class="message-content">${msg.content}</p>`;

                            // Append the new message to the message list
                            messageList.appendChild(li);
                        } else {
                            console.error("Đối tượng tin nhắn thiếu các trường bắt buộc:", msg);
                        }
                    });
                })
                .catch(error => console.error('Lỗi tải tin nhắn:', error));
    }

    function listenToFirebaseMessages() {
        const firebaseConfig = {
            apiKey: "AIzaSyAVeCyZ80YXqniMEBMzG_xRbC_D2Hy77sU",
            authDomain: "equipment-75276.firebaseapp.com",
            databaseURL: "https://equipment-75276-default-rtdb.firebaseio.com",
            projectId: "equipment-75276",
            storageBucket: "equipment-75276.appspot.com",
            messagingSenderId: "518117555751",
            appId: "1:518117555751:web:81bb4ecc0b6610dcb21802",
            measurementId: "G-V36KFTWWL6"
        };

        firebase.initializeApp(firebaseConfig);

        const db = firebase.database();

        db.ref('messages').on('child_added', function (snapshot) {
            const message = snapshot.val();

            if (message && message.sender && message.content) {
                const messageList = document.getElementById('messageList');
                const li = document.createElement('li');


                //li.className = message.type === 'CHAT' ? 'chat-message' : 'event-message';

                if (message.sender === '<s:authentication property="principal.username"/>') {
                    li.className = 'chat-message';
                    li.innerHTML = '<span class="sender">' + message.sender + '</span> <p class="message-content">' + message.content + '</p>';
                } else {
                    li.className = 'event-message';
                    li.innerHTML = '<span class="sender">' + message.sender + '</span> <p class="message-content">' + message.content + '</p>';
                }

                //li.innerHTML = '<span class="sender">' + message.sender + ':</span> <span class="message-content">' + message.content + '</span>';
                messageList.appendChild(li);

                // Scroll to the bottom of the list to show the latest message
                messageList.scrollTop = messageList.scrollHeight;

            } else {
                console.error("Đối tượng tin nhắn thiếu các trường bắt buộc:", message);
            }
        });
    }

    window.onload = function () {
        setupEventListeners();
        listenToFirebaseMessages();
    };
</script>

