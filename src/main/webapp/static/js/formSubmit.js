//відправить форму
function formSubmit(formId){
    document.getElementById(formId).submit()
}
//ініціює подію click для submitButton
function clickSubmitButton(submitButtonId){
    document.getElementById(submitButtonId).click()
}

function selectOption(optionId, setSelected){
    option = document.getElementById(optionId)
    if (setSelected === true){
        option.setAttribute('selected', 'selected')
    }
}

function setSortOptionSelected(optionId, expectedValue){
    option = document.getElementById(optionId)

    if (option.value.toLowerCase() === expectedValue.toLowerCase()){
        option.setAttribute('selected', 'selected')
    }
}

function setSortOptionOrder(sortSelectOrderId, sortSelectCriterionId){

    selectCriterion = document.getElementById(sortSelectCriterionId)
    optionCriterion = selectCriterion.options[selectCriterion.selectedIndex]

    selectOrder = document.getElementById(sortSelectOrderId)

    optionOrder = selectOrder.options[selectOrder.selectedIndex]
    value = optionCriterion.getAttribute('value')
    value = value.replace(/(,[a-zA-Z]*)/, '')
    value = value + "," + optionOrder.getAttribute('value')


    optionCriterion.setAttribute('value', value)
}

function setPageNumber(pageNumber){
    document.getElementById('pageNumber').setAttribute('value', pageNumber)
}

function setPageSize(pageSize){
    document.getElementById('pageSize').setAttribute('value', pageSize)
}

function setValueById(id, value){
    document.getElementById(id).setAttribute('value', value)
}