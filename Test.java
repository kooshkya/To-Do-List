import java.util.Scanner;

class DynamicArray  // We abuse the fact that all Objects inherit from the Object class in Java
{
    private Object array[];
    private int count;

    DynamicArray(int initialSize)
    {
        array = new Object[initialSize];
        count = 0;
    }

    public void add(Object newItem)
    {
        if (count == array.length)  // If it is needed, extend the storage space
        {
            Object newArray[] = new Object[count * 2];
            for (int i = 0; i < count; i++)
            {
                newArray[i] = array[i];
            }
            array = newArray;
        }
        else
        {
            array[count] = newItem;
            count += 1;
        }
    }

    public Object readIndex(int index)  // I would have overloaded the [] operator if stupid java supported it, but OH NO, EVVERYBODY SHOUD USE FUCKING METHODS
    {
        return array[index];
    }

    public int getLength()
    {
        return count;
    }
    public int getCapacity()
    {
        return array.length;
    }
}

class Task
{
    String description;
    int priority_level;

    Task(String description, int priority_level)
    {
        this.description = description;
        this.priority_level = priority_level;
    }
}

class Input     // This class is in charge of all user inputs   // I wanted to make this a child class of Scanner, instead of including an instance of Scanner in it. But scanner is a "final" class, which in java means it can't be inherited :(
{
    private Scanner scanner;

    Input()
    {
        scanner = new Scanner(System.in);
    }
    public int getIntInRange(int lowerBound, int higherBound)
    {
        int input;
        do
        {
            input = scanner.nextInt();
        }
        while (input < lowerBound || input >= higherBound);

        return input;
    }
    public String getString()
    {
        return scanner.next();
    }
}

public class Test
{
    private Input input = new Input();
    private boolean quit = false;
    DynamicArray task_list = new DynamicArray(2);

    public static void main(String[] args)
    {
        Test me = new Test();   // Because non-static memebers(like quit and scanner) are not accessible from within static functions(which makes sense if you know what static means), we have two choices: make every method and field static, or instantiate this class and manipulate that object's fields and call its methods. I am uncertain as to which is a better practice.
        System.out.println("Welcome to Task Manager! Enter a command:");
        while (!me.quit)
        {
            me.quit = me.get_command();
        }
    }

    private boolean get_command()
    {
        boolean return_value = false;
        String command = input.getString();
        switch (command)
        {
            case "add":
                add_user();
                break;
            case "show":
                show_users();
                break;
            case "end":
                return_value = true;
                break;
        }
        return return_value;
    }

    public void add_user()
    {
        System.out.println("Enter task title:");
        String title = input.getString();
        System.out.println("Enter task priority (from 1 to 5)");
        int priority = input.getIntInRange(1, 6);
        task_list.add(new Task(title, priority));
    }

    public void show_users()
    {
        for (int i = 0; i < task_list.getLength(); i++)  // The foreach loop (for (Task task : task_list)) does not work here, because it 
        {
            System.out.println(String.format("Description: %s\npriority: %d", ((Task)task_list.readIndex(i)).description, ((Task)task_list.readIndex(i)).priority_level));
        }
    }
}