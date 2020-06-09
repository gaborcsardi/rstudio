 * Copyright (C) 2009-19 by RStudio, PBC
#if _WIN32
    * @param overwrite          Whether to overwrite the file if one exists in target path.
   Error copy(const FilePath& in_targetPath, bool overwrite = false) const;
    * @param overwrite          Whether to overwrite the file if one exists in target path.
   Error copyDirectoryRecursive(const FilePath& in_targetPath, bool overwrite = false) const;
#if _WIN32
    * @param overwrite          Whether to overwrite the file if one exists in target path.
   Error move(const FilePath& in_targetPath, MoveType in_type = MoveCrossDevice, bool overwrite = false) const;
    * @param overwrite          Whether to overwrite the file if one exists in target path.
   Error moveIndirect(const FilePath& in_targetPath, bool overwrite = false) const;
    * If write access is not absolutely necessary, use isFileWriteable from FileMode.hpp.