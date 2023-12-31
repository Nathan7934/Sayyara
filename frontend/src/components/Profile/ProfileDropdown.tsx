import Menu from '@mui/material/Menu';
import AccountCircle from "@mui/icons-material/AccountCircle";
import { IconButton, MenuItem } from "@mui/material";
import React from "react";
import { Link } from "react-router-dom";
import { useLogout } from 'src/utilities/hooks/useLogout';

const ProfileDropdown = () => {
    const [anchorEl, setAnchorEl] = React.useState<null | HTMLElement>(null);
    const { logout } = useLogout();
    const open = Boolean(anchorEl);
    const handleClick = (event: React.MouseEvent<HTMLButtonElement>) => {
        setAnchorEl(event.currentTarget);
    };
    const handleClose = () => {
        setAnchorEl(null);
    };
    const onLogout = () => {
        logout();
        handleClose();
    }
    return (
        <div className="">
            <IconButton
                aria-controls={open ? 'basic-menu' : undefined}
                aria-haspopup="true"
                aria-expanded={open ? 'true' : undefined}
                onClick={handleClick}
            >
                <AccountCircle />
            </IconButton>
            <Menu
                id="basic-menu"
                anchorEl={anchorEl}
                open={open}
                onClose={handleClose}
                MenuListProps={{
                    'aria-labelledby': 'basic-button',
                }}
            >
                <Link to='/profile' style={{ textDecoration: 'none' }}>
                    <MenuItem onClick={handleClose}>Profile</MenuItem>
                </Link>
                <Link to='/' style={{ textDecoration: 'none' }}>
                    <MenuItem onClick={onLogout}>Log Out</MenuItem>
                </Link>
            </Menu>
        </div>
    )
}
export default ProfileDropdown;