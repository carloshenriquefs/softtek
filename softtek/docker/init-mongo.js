// Script de inicialização do MongoDB para a aplicação de Saúde Mental
// Este script cria o banco de dados e um usuário específico para a aplicação

db = db.getSiblingDB('saude_mental');

// Criar usuário para a aplicação
db.createUser({
  user: 'saude_mental_user',
  pwd: 'saude_mental_pass',
  roles: [
    {
      role: 'readWrite',
      db: 'saude_mental'
    }
  ]
});

// Criar coleções básicas (opcional - serão criadas automaticamente pela aplicação)
db.createCollection('users');
db.createCollection('assessments');
db.createCollection('questions');
db.createCollection('resources');
db.createCollection('audit_logs');

print('Database saude_mental initialized successfully!');
