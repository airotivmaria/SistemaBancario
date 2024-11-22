package DTO;

public class Usuario {
        private String nome;
        private int idade;
        private String cpf;
        private String senha;
        private Conta conta;

        //GETTERS E SETTERS
        public String getSenha() {
            return senha;
        }

        public void setSenha(String senha) {
            this.senha = senha;
        }

        public String getCpf() {
            return cpf;
        }

        public void setCpf(String cpf) {
            this.cpf = cpf;
        }

        public int getIdade() {
            return idade;
        }

        public void setIdade(int idade) {
            this.idade = idade;
        }

        public String getNome() {
            return nome;
        }

        public void setNome(String nome) {
            this.nome = nome;
        }

        public Conta getConta() {
            return conta;
        }

        public void setConta(Conta conta) {
            this.conta = conta;
        }

}
