
function setChangingMenuItemsHandlers(profileMenuItemsId){
    let childNodes = document.getElementById(profileMenuItemsId).childNodes;

    for(let child of childNodes){
        console.log(child)
        child.addEventListener('click', ()=>{
            //найде вибраний елемент
            let el = document.querySelector(`ul[id=${profileMenuItemsId}] li.nav-item button.nav-link.active`)
            activePillHiddenInput.setAttribute('value', el.id)
            console.log('id:' + el.id)
        })
    }
}