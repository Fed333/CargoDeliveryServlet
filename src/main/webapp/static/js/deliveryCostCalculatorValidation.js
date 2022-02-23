
function removeDataErrorTag(elementId){
    element = document.getElementById(elementId)
    element.removeAttribute('data-error')
}

function addRemoveDataErrorTagSelectCityListeners(selectCityFromId, selectCityToId, removeDataErrorTagFunction){
    cityFromSelect = document.getElementById(selectCityFromId)
    cityToSelect = document.getElementById(selectCityToId)

    cityFromSelect.addEventListener('change', removeDataErrorTagFunction, false)
    cityToSelect.addEventListener('change', removeDataErrorTagFunction, false)

}

