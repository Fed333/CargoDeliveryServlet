
function paginate(containerId, sendRequestFunction) {
    let container = document.querySelector(`#${containerId}`)
    container.querySelectorAll(".pagination .page-item .page-link[page-number]").forEach(
        refElement => {
            refElement.addEventListener("click", ()=>{
                let page = document.getElementById("pageNumber")
                page.setAttribute("value", refElement.getAttribute("page-number"))
            })
            refElement.addEventListener("click", sendRequestFunction)
        }
    )
}
