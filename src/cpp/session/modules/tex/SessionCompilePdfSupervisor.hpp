/*
 * SessionCompilePdfSupervisor.hpp
 *
 * Copyright (C) 2009-11 by RStudio, Inc.
 *
 * This program is licensed to you under the terms of version 3 of the
 * GNU Affero General Public License. This program is distributed WITHOUT
 * ANY EXPRESS OR IMPLIED WARRANTY, INCLUDING THOSE OF NON-INFRINGEMENT,
 * MERCHANTABILITY OR FITNESS FOR A PARTICULAR PURPOSE. Please refer to the
 * AGPL (http://www.gnu.org/licenses/agpl-3.0.txt) for more details.
 *
 */

#ifndef SESSION_MODULES_TEX_COMPILE_PDF_SUPERVISOR_HPP
#define SESSION_MODULES_TEX_COMPILE_PDF_SUPERVISOR_HPP

#include <string>
#include <vector>

#include <core/system/Types.hpp>

#include <boost/function.hpp>

namespace core {
   class Error;
   class FilePath;
   namespace system {
      struct ProcessOptions;
   }
}
 
namespace session {
namespace modules { 
namespace tex {
namespace compile_pdf_supervisor {


bool hasRunningChildren();

core::Error runProgram(const core::FilePath& programFilePath,
                       const std::vector<std::string>& args,
                       const core::system::Options& extraEnvVars,
                       const core::FilePath& workingDir,
                       const boost::function<void(const std::string&)>& onOutput,
                       const boost::function<void(int)>& onExited);

core::Error initialize();

} // namespace compile_pdf_supervisor
} // namespace tex
} // namespace modules
} // namesapce session

#endif // SESSION_MODULES_TEX_COMPILE_PDF_SUPERVISOR_HPP
