/* global Swal */

function deleteEquipment(endpoint, id) {
    let dele = document.getElementById(`equipment${id}`);
    let productName = dele.getAttribute('data-name');
    let productCode = dele.getAttribute('data-code');
    Swal.fire({
        title: `Bạn có chắc chắn xóa sản phẩm "${productName}" có mã "${productCode}" không?`,
        icon: 'warning',
        showCancelButton: true,
        confirmButtonText: 'Đồng ý',
        cancelButtonText: 'Không'
    }).then((result) => {
        if (result.isConfirmed) {
            fetch(endpoint, {
                method: "delete"
            }).then(res => {
                console.log(res);
                if (res.status === 204) {
                    dele.style="display: none";
                    Swal.fire('Đã xóa!', 'Sản phẩm đã được xóa.', 'success');
                } else {
                    console.error("Lỗi xóa");
                    Swal.fire('Lỗi!', 'Không thể xóa sản phẩm.', 'error');
                }
            });
        }
    });
}


const formatDate = (date) => {
  const day = date.getDate().toString().padStart(2, '0');
  const month = (date.getMonth() + 1).toString().padStart(2, '0'); // Tháng trong JavaScript bắt đầu từ 0
  const year = date.getFullYear();

  return `Ngày ${day} tháng ${month} năm ${year}.`;
};


