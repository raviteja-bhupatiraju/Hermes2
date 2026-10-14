; Inno Setup script of the Windows installer of Hermes 2 (built by "mvnw -Prelease verify", see pom.xml).
;
; Defines passed by the build:
;   AppVersion  the version, e.g. 1.0.0
;   SourceDir   the materialized Windows product (Hermes2 folder; its Java runtime is the JustJ plug-in it includes)
;   OutputDir   where the setup is written (dist\)
;   IconFile    the application icon (.ico)

#ifndef AppVersion
  #define AppVersion "1.0.0"
#endif

#define AppName "Hermes 2: MedBiquitous Editor"
#define AppPublisher "University of Louisiana at Lafayette, Center for Applied Artificial Intelligence (CAAI)"

[Setup]
AppId={{6B0E8F3A-2C4D-4E7B-9A51-3F2D8C1E7A42}
AppName={#AppName}
AppVersion={#AppVersion}
AppVerName={#AppName} {#AppVersion}
; Tells Windows that icons changed, so that shortcuts don't keep a cached icon of an earlier Hermes2.exe.
ChangesAssociations=yes
AppPublisher={#AppPublisher}
AppPublisherURL=https://louisiana.edu/
AppSupportURL=https://www.medbiq.org/standards
VersionInfoVersion={#AppVersion}
VersionInfoCompany={#AppPublisher}
VersionInfoDescription={#AppName} Setup
DefaultDirName={autopf}\Hermes 2
DefaultGroupName=Hermes 2
DisableProgramGroupPage=yes
; Installs for all users (as administrator) or, if chosen in the dialog, for the current user only.
PrivilegesRequired=admin
PrivilegesRequiredOverridesAllowed=dialog
ArchitecturesAllowed=x64compatible
ArchitecturesInstallIn64BitMode=x64compatible
MinVersion=10.0
OutputDir={#OutputDir}
OutputBaseFilename=Hermes2-{#AppVersion}-windows-x64-setup
SetupIconFile={#IconFile}
UninstallDisplayIcon={app}\Hermes2.exe
UninstallDisplayName={#AppName}
WizardStyle=modern
WizardImageFile=wizard.bmp
WizardSmallImageFile=wizard-small.bmp
Compression=lzma2/ultra64
SolidCompression=yes
LZMAUseSeparateProcess=yes
CloseApplications=yes
ChangesEnvironment=yes

[Languages]
Name: "english"; MessagesFile: "compiler:Default.isl"

[Tasks]
Name: "desktopicon"; Description: "{cm:CreateDesktopIcon}"; GroupDescription: "{cm:AdditionalIcons}"; Flags: unchecked
Name: "addtopath"; Description: "Add the hermes command line tool (validation, conversion, language server) to the PATH"; GroupDescription: "Command line:"; Flags: unchecked

[Files]
Source: "{#SourceDir}\*"; DestDir: "{app}"; Flags: ignoreversion recursesubdirs createallsubdirs

[Icons]
Name: "{autoprograms}\Hermes 2"; Filename: "{app}\Hermes2.exe"; Comment: "MedBiquitous Editor"
Name: "{autodesktop}\Hermes 2"; Filename: "{app}\Hermes2.exe"; Tasks: desktopicon; Comment: "MedBiquitous Editor"

[Run]
Filename: "{app}\Hermes2.exe"; Description: "{cm:LaunchProgram,Hermes 2}"; Flags: nowait postinstall skipifsilent

[Code]
const
  // The deepest files are about 170 characters below the installation folder; Windows paths are limited to 260.
  MaxDirLength = 80;
  SystemEnvironment = 'SYSTEM\CurrentControlSet\Control\Session Manager\Environment';
  UserEnvironment = 'Environment';

// The PATH of all users (administrative install) or of the current user.
function PathRoot: Integer;
begin
  if IsAdminInstallMode then Result := HKEY_LOCAL_MACHINE else Result := HKEY_CURRENT_USER;
end;

function PathKey: String;
begin
  if IsAdminInstallMode then Result := SystemEnvironment else Result := UserEnvironment;
end;

function CliDir: String;
begin
  Result := ExpandConstant('{app}\cli');
end;

function PathContains(Path, Dir: String): Boolean;
begin
  Result := Pos(';' + Uppercase(Dir) + ';', ';' + Uppercase(Path) + ';') > 0;
end;

procedure AddCliToPath;
var
  Path: String;
begin
  if not RegQueryStringValue(PathRoot, PathKey, 'Path', Path) then Path := '';
  if not PathContains(Path, CliDir) then
  begin
    if (Path <> '') and (Copy(Path, Length(Path), 1) <> ';') then Path := Path + ';';
    RegWriteExpandStringValue(PathRoot, PathKey, 'Path', Path + CliDir);
  end;
end;

procedure RemoveCliFromPath;
var
  Path: String;
  Index: Integer;
begin
  if RegQueryStringValue(PathRoot, PathKey, 'Path', Path) and PathContains(Path, CliDir) then
  begin
    Path := ';' + Path + ';';
    Index := Pos(';' + Uppercase(CliDir) + ';', Uppercase(Path));
    Delete(Path, Index, Length(CliDir) + 1);
    Path := Copy(Path, 2, Length(Path) - 2);
    RegWriteExpandStringValue(PathRoot, PathKey, 'Path', Path);
  end;
end;

procedure CurStepChanged(CurStep: TSetupStep);
begin
  if (CurStep = ssPostInstall) and WizardIsTaskSelected('addtopath') then AddCliToPath;
end;

procedure CurUninstallStepChanged(CurUninstallStep: TUninstallStep);
begin
  if CurUninstallStep = usPostUninstall then RemoveCliFromPath;
end;

function NextButtonClick(CurPageID: Integer): Boolean;
begin
  Result := True;
  if (CurPageID = wpSelectDir) and (Length(WizardDirValue) > MaxDirLength) then
  begin
    MsgBox('Please choose a folder with a shorter path (at most ' + IntToStr(MaxDirLength) +
      ' characters), for example ' + ExpandConstant('{autopf}\Hermes 2') + '.', mbError, MB_OK);
    Result := False;
  end;
end;

[UninstallDelete]
; Created by the application at run time (caches of the OSGi framework next to the installation).
Type: filesandordirs; Name: "{app}\configuration\org.eclipse.*"
Type: filesandordirs; Name: "{app}\p2"
