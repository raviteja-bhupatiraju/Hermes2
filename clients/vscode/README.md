# Hermes 2 for Visual Studio Code

Checks MedBiquitous Healthcare LOM documents (XML and JSON) while you type, with the same checks as the Hermes 2
editor: XML Schema, model constraints and the MedBiquitous rules. It also offers:

- quick fixes (Ctrl+.) and *Fix all automatically fixable problems*;
- hovers with the help text of the field under the mouse;
- *Format Document* in the canonical layout of the document's form;
- the outline of the document's elements (XML).

Other XML and JSON files are left alone.

## Setup

1. Install Hermes 2 (the `cli` folder of the installation holds the `hermes` launcher) or unpack
   `hermes-cli-<version>.zip` (needs Java 25 or later).
2. Put the `cli` folder on the `PATH` (the Windows installer can do this), or set **Hermes: Path** (`hermes.path`)
   to the launcher, for example `C:\Program Files\Hermes 2\cli\hermes.cmd`.
3. Build and install the extension:

   ```sh
   npm install
   npx @vscode/vsce package
   code --install-extension hermes-medbiquitous-1.0.0.vsix
   ```

Any other editor with Language Server Protocol support can use the server the same way: run `hermes lsp` (standard
input/output) for XML and JSON files.
