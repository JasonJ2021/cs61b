package gitlet;


/**
 * Driver class for Gitlet, a subset of the Git version-control system.
 *
 * @author JasonJ2021
 */
public class Main {

    /**
     * Usage: java gitlet.Main ARGS, where ARGS contains
     * <COMMAND> <OPERAND1> <OPERAND2> ...
     */
    public static void main(String[] args) {
        //
        if (args.length == 0) {
            System.out.println("Please enter a command.");
            System.exit(0);
        }
        String firstArg = args[0];
        switch (firstArg) {
            case "init":
                Repository.init();
                break;
            case "add":
                String filename = args[1];
                Repository.add(filename);
                break;
            //
            case "commit":

                String message = args[1];
                Repository.commit(message);
                break;
            case "rm":

                Repository.rm(args[1]);
                break;
            case "log":

                Repository.log();
                break;
            case "checkout":

                if (args.length == 3) {
                    //java gitlet.Main checkout -- [file name]
                    Repository.checkout1(args[2]);
                } else if (args.length == 4) {
                    //java gitlet.Main checkout [commit id] -- [file name]
                    if (!args[2].equals("--")) {
                        System.out.println("Incorrect operands.");
                        System.exit(0);
                    }
                    Repository.checkout2(args[1], args[3]);
                } else {
                    //java gitlet.Main checkout [branch name]
                    Repository.checkout3(args[1]);
                }
                break;
            case "branch":
                Repository.branch(args[1]);
                break;
            case "status":

                Repository.status();
                break;
            case "global-log":

                Repository.global_log();
                break;
            case "find":
                Repository.find(args[1]);
                break;
            case "rm-branch":
                Repository.rm_branch(args[1]);
                break;
            case "merge":
                Repository.merge(args[1]);
                break;
            case "reset":
                //java gitlet.Main reset [commit id]
                Repository.reset(args[1]);
                break;
            case "add-remote":
                //java gitlet.Main add-remote [remote name] [name of remote directory]/.gitlet
                Repository.add_remote(args[1], args[2]);
                break;
            case "rm-remote":
                //java gitlet.Main rm-remote [remote name]
                Repository.rm_remote(args[1]);
                break;
            case "push":
                //java gitlet.Main push [remote name] [remote branch name]
                Repository.push(args[1] , args[2]);
                break;
            case "fetch":
                //java gitlet.Main fetch [remote name] [remote branch name]
                Repository.fetch(args[1] , args[2]);
                break;
            case "pull":
                //java gitlet.Main fetch [remote name] [remote branch name]
                Repository.pull(args[1] , args[2]);
                break;
            default:
                System.out.println("No command with that name exists.");
                System.exit(0);
        }
    }
}
