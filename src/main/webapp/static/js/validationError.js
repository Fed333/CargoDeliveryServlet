
function addRemoveErrorAttributeListener(){

    document.querySelectorAll('.validation-container[data-error] .form-control').forEach(
        inputElement => {
            inputElement.addEventListener('input', () => inputElement.parentElement.removeAttribute('data-error'))
        }
    )
}