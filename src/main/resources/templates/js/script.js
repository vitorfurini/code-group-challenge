const formulario = document.querySelector("form");
const InomeProjeto = document.querySelector(".nomeProjeto");
const IdescricaoProjeto = document.querySelector(".descricaoProjeto");
const IdataInicio = document.querySelector(".dataInicio");
const IprevisaoFim = document.querySelector(".dataInicio");
const IdataFim = document.querySelector(".dataInicio");
const Iorcamento = document.querySelector(".orcamento");
const Igerente = document.querySelector(".gerente");


function cadastrarProjetos() {

    fetch("http://localhost:8080/api/project",
        {
            headers: {
                'Accept': 'application/json',
                'Content-Type': 'application/json'
            },
            method: "POST",
            body: JSON.stringify({
                nome: InomeProjeto.value,
                dataInicio: IdataInicio.value,
                dataPrevisaoFim: IprevisaoFim.value,
                dataFim: IdataFim.value,
                descricao: IdescricaoProjeto.value,
                orcamento: Iorcamento.value,
                gerente: {
                    id: Igerente.value
                }
            })
        })
        .catch(function (res) { console.log(res) })

}

function listarProjetos() {

    fetch("http://localhost:8080/api/project")

        .then(response => response.json())
        .then(data => {
            const tableBody = document.querySelector("tbody");

            data.forEach(item => {
                const row = tableBody.insertRow();
                const cell1 = row.insertCell(0);
                const cell2 = row.insertCell(1);
                const cell3 = row.insertCell(2);
                const cell4 = row.insertCell(3);
                const cell5 = row.insertCell(4);
                const cell6 = row.insertCell(5);

                cell1.textContent = item.nome;
                cell2.textContent = item.gerente.nome;
                cell3.textContent = item.gerente;
                cell3.textContent = formateDate(item.dataInicio);
                cell4.textContent = formateDate(item.dataFim);
                cell5.textContent = formateDate(item.dataPrevisaoFim);

                let imgEdit = document.createElement('img');
                imgEdit.src = ('../img/edit.svg')


                cell6.appendChild(imgEdit);

                let imgDelete = document.createElement('img');
                imgDelete.src = '../img/delete.svg'
                imgDelete.setAttribute("onclick", "deletar(" + JSON.stringify(item) + ")")
                cell6.appendChild(imgDelete);

            })
        });
}

function deletar(item) {
    if (confirm('Deseja excluir o projeto ' + item.nome + ' ?')) {

        fetch("http://localhost:8080/api/project/" + item.id, {
            method: 'DELETE',
        })
            .catch(function (res) { alert(res) })
    }
}

function formateDate(dataFormat) {

    const dia = String(new Date(dataFormat).getDate());
    const mes = String(new Date(dataFormat).getMonth());
    const ano = new Date(dataFormat).getFullYear();

    const dataFormatada = `${dia}/${mes}/${ano} `;
    return dataFormatada;
}

function formateDateToDisplay(dataFormat) {

    const dia = String(new Date(dataFormat).getDate());
    const mes = String(new Date(dataFormat).getMonth());
    const ano = new Date(dataFormat).getFullYear();

    const dataFormatada = `${ano}-${mes}-${dia} `;
    return dataFormatada;
}

document.addEventListener("DOMContentLoaded", function () {
    listarProjetos();
});


formulario.addEventListener('submit', function (event) {
    event.preventDefault();

    cadastrarProjetos();
    limpar();

});

function limpar() {
    InomeProjeto.value = "",
        IdataInicio.value = "",
        IprevisaoFim.value = "",
        IdataFim.value = "",
        IdescricaoProjeto.value = "",
        Iorcamento.value = "",
        Igerente.value = ""

}