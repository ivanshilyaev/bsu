function checkbox() {
    const alphabet = "АБВГДЕЁЖЗИЙКЛМНОПРСТУФХЦЧШЩЪЫЬЭЮЯ "
    let checkBox = document.getElementById("default-alphabet");
    if (checkBox.checked === true) {
        document.getElementById("alphabet").value = alphabet;
    } else {
        document.getElementById("alphabet").value = "";
    }
}