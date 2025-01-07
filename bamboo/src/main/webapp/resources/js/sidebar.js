
const hamburger = document.querySelector('#toggle-btn');
const sidebar = document.querySelector("#sidebar");
const mainContent = document.querySelector(".main");

hamburger.addEventListener("click", function () {
    sidebar.classList.toggle("expand");
    mainContent.classList.toggle("expand");
});

function markAsRead(notificationId) {
    fetch(`/bamboo/mark-as-read/${notificationId}`, {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify({}) // Nếu cần gửi dữ liệu thêm, thêm vào đây
    })
            .then(response => {
                if (response.ok) {
                    // Giảm số lượng thông báo chưa đọc
                    const unreadCountElement = document.getElementById('unread-count');
                    const unreadCount = parseInt(unreadCountElement.innerText);
                    if (unreadCount > 0) {
                        unreadCountElement.innerText = unreadCount - 1;
                    }

                    // Xử lý thành công, ẩn thông báo
                    const notificationItem = document.querySelector(`li[data-notification-id='${notificationId}']`);
                    notificationItem.classList.remove('unread');
                    notificationItem.classList.add('read');
                    const statusText = notificationItem.querySelector('.notification-status');
                    statusText.textContent = 'Đã đọc';
                } else {
                    throw new Error('Có lỗi xảy ra khi đánh dấu thông báo đã đọc.');
                }
            })
            .catch(error => {
                console.error('Lỗi:', error);
                alert('Đánh dấu không thành công: ' + error.message);
            });
}

document.querySelectorAll('.delete-notification').forEach(function (btn) {
    btn.addEventListener('click', function (event) {
        event.stopPropagation(); // Ngăn chặn sự kiện click của thông báo

        let notificationItem = this.closest('.notification-item');
        notificationItem.style.display = "none";
    });
});