
langItems = {
    'en' : langEnglish,
    'uk' : langUkrainian
}

function switchLanguageToUkrainianLocale(){
    switchLanguageToLocale('uk')
}

function switchLanguageToEnglishLocale(){
    switchLanguageToLocale('en')
}

function switchLanguageToLocale(lang){
     langInput.setAttribute('value', lang)
}

function addSwitchLanguageListeners(){
    langEnglish.addEventListener('click', switchLanguageToEnglishLocale)
    langUkrainian.addEventListener('click', switchLanguageToUkrainianLocale)
}

function addEventListeners(submitHandler){
    langEnglish.addEventListener('click', switchLanguageToEnglishLocale)
    langUkrainian.addEventListener('click', switchLanguageToUkrainianLocale)

    langEnglish.addEventListener('click', submitHandler)
    langUkrainian.addEventListener('click', submitHandler)
}

function getTextInputData(textInput){
    return textInput.value
}

function getSelectData(select){
    select[select.selectedIndex]
}

//дістане дані форми по id з inputs у вигляді мапи
function getInputData(inputs){
    let dict = {}
    for(let id of inputs){
        input = document.getElementById(id)

        if (input !== null){
            dict[input.name] = input.value
        }

    }
    return dict
}

//закодує дані з словника data у належний url рядок
function encodeData(data){
    let ret = []
    for(let d in data){
        ret.push(encodeURIComponent(d) + '=' + encodeURIComponent(data[d]))
    }
    return ret.join('&')
}

//встановить атрибут ролі для всіх елементів вибору мови
function setRoleToSwitchLanguageItems(role){
    langEnglish.setAttribute('role', role)
    langUkrainian.setAttribute('role', role)
}

//сформує href для обраного елементу вибору мови, lang in ['uk', 'en']
//inputs - id потрібних input елементів
function switchLanguageToLocaleWithUrl(url, inputs, lang){

    let data = getInputData(inputs)
    data['lang'] = lang

    langItem = langItems[lang]

    let href = url + "?" + encodeData(data)
    langItem.setAttribute('href', href)
    window.location.href = langItem.href
}

//inputs - id of inputs elements
function switchLanguageToUkrainianLocaleWithUrl(url, inputs){
    switchLanguageToLocaleWithUrl(url, inputs, 'uk')
}

function switchLanguageToEnglishLocaleWithUrl(url, inputs){
    switchLanguageToLocaleWithUrl(url, inputs, 'en')
}

function addSwitchLanguageWithUrlClickListeners(url, inputs){

    langUkrainian.addEventListener('click', ()=>{switchLanguageToUkrainianLocaleWithUrl(url, inputs)})
    langEnglish.addEventListener('click', ()=>{switchLanguageToEnglishLocaleWithUrl(url, inputs)})

}