import { useState } from 'react';

const useValidation = () => {
    const [errors, setErrors] = useState({});

    const validateFields = (fields) => {
        const newErrors = {};

        fields.forEach(({ name, value, rules, ref }) => {
            if (rules.required && !value) {
                newErrors[name] = `${rules.label} là bắt buộc`;
                if (ref && ref.current) {
                    ref.current.shake(); // Nếu bạn có hàm shake() để rung input khi có lỗi
                }
            }
            if (rules.minLength && value.length < rules.minLength) {
                newErrors[name] = `${rules.label} phải có ít nhất ${rules.minLength} ký tự`;
            }
            // Thêm các validate khác nếu cần
        });

        setErrors(newErrors);
        return Object.keys(newErrors).length === 0;
    };

    return {
        errors,
        validateFields,
    };
};

export default useValidation;
