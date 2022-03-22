
function paginate(containerId, pageNumberId, sendRequestFunction) {
    let container = document.querySelector(`#${containerId}`)
    container.querySelectorAll(".pagination .page-item .page-link[page-number]").forEach(
        refElement => {
            refElement.addEventListener("click", ()=>{
                let page = document.getElementById(pageNumberId)
                page.setAttribute("value", refElement.getAttribute("page-number"))
            })
            refElement.addEventListener("click", sendRequestFunction)
        }
    )
}
