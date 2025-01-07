export const formatPrice = (price) => {
    // Nhân giá với 1 triệu (hoặc thêm 6 số 0)
    const formattedPrice = price * 1000;

    // Chuyển số thành chuỗi
    const priceString = formattedPrice.toString();

    // Tạo mảng chứa các ký tự
    const characters = priceString.split('');

    // Chèn dấu chấm sau mỗi 3 số từ phía sau
    for (let i = characters.length - 3; i > 0; i -= 3) {
        characters.splice(i, 0, '.');
    }

    // Nối lại thành chuỗi và thêm ' VNĐ' ở cuối
    return characters.join('') + ` VNĐ`;
};
