// Copyright (c) 2026 University of Louisiana at Lafayette, CAAI.
// Starts the Hermes 2 language server ("hermes lsp") for XML and JSON files; the server only reports on
// MedBiquitous documents and stays silent for other files.
const vscode = require('vscode');
const { LanguageClient, TransportKind } = require('vscode-languageclient/node');

let client;

function createClient() {
  const configured = vscode.workspace.getConfiguration('hermes').get('path');
  const command = configured && configured.trim() !== ''
    ? configured
    : (process.platform === 'win32' ? 'hermes.cmd' : 'hermes');
  const serverOptions = {
    command,
    args: ['lsp'],
    transport: TransportKind.stdio,
    options: { shell: process.platform === 'win32' && command.toLowerCase().endsWith('.cmd') }
  };
  const clientOptions = {
    documentSelector: [
      { scheme: 'file', language: 'xml' },
      { scheme: 'untitled', language: 'xml' },
      { scheme: 'file', language: 'json' },
      { scheme: 'untitled', language: 'json' }
    ]
  };
  return new LanguageClient('hermes', 'Hermes 2', serverOptions, clientOptions);
}

async function activate(context) {
  client = createClient();
  context.subscriptions.push(vscode.commands.registerCommand('hermes.restart', async () => {
    if (client) {
      await client.stop();
    }
    client = createClient();
    await client.start();
  }));
  await client.start();
}

async function deactivate() {
  if (client) {
    await client.stop();
  }
}

module.exports = { activate, deactivate };
