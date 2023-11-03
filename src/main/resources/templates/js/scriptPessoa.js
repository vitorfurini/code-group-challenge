const formulario = document.querySelector("form");
const InomePessoa = document.querySelector(".nomePessoa");
const Icpf = document.querySelector(".cpf");
const IdataNascimento = document.querySelector(".dataNascimento");
const IisFuncionario = document.querySelector(".isFuncionario");



function listarPessoas() {

    fetch("http://localhost:8080/api/pessoa")

        .then(response => response.json())
        .then(data => {
            const tableBody = document.querySelector("tbody");

            data.forEach(pessoa => {
                const row = tableBody.insertRow();
                const cell1 = row.insertCell(0);
                const cell2 = row.insertCell(1);
                const cell3 = row.insertCell(2);
                

                cell1.textContent = pessoa.id;
                cell2.textContent = pessoa.name;
                cell3.textContent = formataCPF(pessoa.cpf);

            })
        });
}

function cadastrarPessoas() {

    fetch("http://localhost:8080/api/pessoa",
        {
            headers: {
                'Accept': 'application/json',
                'Content-Type': 'application/json'
            },
            method: "POST",
            body: JSON.stringify({
                name: InomePessoa.value,
                cpf: Icpf.value,
                isFuncionario: IisFuncionario.value,
                dataNascimento: IdataNascimento.value
            })
        })
        .catch(function (res) { console.log(res) })

}



function formateDate(dataFormat) {

    const dia = String(new Date(dataFormat).getDate());
    const mes = String(new Date(dataFormat).getMonth());
    const ano = new Date(dataFormat).getFullYear();

    const dataFormatada = `${dia}/${mes}/${ano} `;
    return dataFormatada;
}

function formataCPF(cpf){
    cpf = cpf.replace(/[^\d]/g, "");
    
      return cpf.replace(/(\d{3})(\d{3})(\d{3})(\d{2})/, "$1.$2.$3-$4");
  }

function formateDateToDisplay(dataFormat) {

    const dia = String(new Date(dataFormat).getDate());
    const mes = String(new Date(dataFormat).getMonth());
    const ano = new Date(dataFormat).getFullYear();

    const dataFormatada = `${ano}-${mes}-${dia} `;
    return dataFormatada;
}

document.addEventListener("DOMContentLoaded", function () {
    listarPessoas();
});

formulario.addEventListener('submit', function (event) {
    event.preventDefault();

    cadastrarPessoas();
    limpar();

});

function limpar() {
    InomePessoa.value = "",
    Icpf.value = "",
    IdataNascimento.value = ""


}